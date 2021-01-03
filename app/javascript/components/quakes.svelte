<div class='center'>
  <div class='left'>
    <input
      placeholder='Search...'
      bind:value={query}
      on:keyup={searchAction}
    >
    <div class='help-button'>
      ?
    </div>
    <ul>
      {#if results.length === 0}
        <li class='empty-message'>
          {#if query === ''}
            Loading...
          {:else}
            No Earthquakes found.
          {/if}
        </li>
      {:else}
        {#each results as quake}
          <li>
            <div class='size'>
              {quake.eq_primary}
            </div>
            <div class='country'>
              {quake.country}
            </div>
            <div class='location'>
              {quake.location}
            </div>
            <div class='date'>
              {quake.date}
            </div>
          </li>
        {/each}
      {/if}
    </ul>
  </div>
  <div class='right'>
    <Map quakes={results}/>
  </div>
</div>

<script>
  import Map from './map.svelte';

  let allQuakes = []
  let results = []
  let selectedResult = null
  let showHelp = false
  let query = ''

  let searchAction = _ => {
    const clean = query.trim()
    const encoded = b64EncodeUnicode(clean)
    window.location = `#/${encoded}`
    if (clean === '') {
      results = allQuakes
      return
    }
    // if (query !== clean) {
    //   return
    // }
    fetch(`/search/${encoded}`)
    .then(x => x.json())
    .then(x => results = fastQuakeFilter(x, allQuakes))
  }

  fetch('quake_export.json')
  .then(x => x.json())
  .then(x => allQuakes = x)
  .then(_ => results = allQuakes)
</script>
