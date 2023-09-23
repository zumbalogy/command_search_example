<div class='center'>
  <div class='left'>
    <input
      placeholder='Search...'
      bind:value={query}
      on:keyup={searchAction}
    >
    <div class='help-button' on:click={_ => showHelp = !showHelp}>
      ?
    </div>
    <QuakeList
      quakes={results}
      query={query}
      bind:selectedQuake={selectedQuake}
    />
  </div>
  <div class='right'>
    {#if showHelp}
      <Help clickAction={x => (query = x) && searchAction()}/>
    {/if}
    <Map quakes={results} bind:selectedQuake={selectedQuake}/>
    {#if selectedQuake}
      <Selected
        selectedQuake={selectedQuake}
        searchAction={searchAction}
        bind:query={query}
      />
    {/if}
  </div>
</div>

<script>
  import QuakeList from './quakeList.svelte'
  import Selected from './selected.svelte'
  import Help from './help.svelte'
  import Map from './map.svelte'
  import {
    fastQuakeFilter,
    b64EncodeUnicode,
    b64DecodeUnicode
  } from './helpers.js'


  let allIds = []
  let allQuakes = []
  let resultIds = []
  let results = []
  let selectedQuake = null
  let showHelp = false
  let query = ''

  $: results = fastQuakeFilter(resultIds, allQuakes)

  const searchAction = _ => {
    const clean = query.trim()
    if (query !== clean) {
      return
    }
    const encoded = b64EncodeUnicode(clean)
    window.location = `#/${encoded}`
    if (clean === '') {
      resultIds = allIds
      return
    }
    const save = query
    fetch(`/search/${encoded}`)
    .then(x => x.json())
    .then(x => (save === query) && (resultIds = x))
  }


  fetch('quake_export.json')
  .then(x => x.json())
  .then(x => allQuakes = x)
  .then(_ => allIds = allQuakes.map(x => x.id))
  .then(_ => resultIds = allIds)

  if (location.hash.length > 2) {
    const hash = location.hash.slice(2)
    query = b64DecodeUnicode(hash)
    searchAction()
  }
</script>
