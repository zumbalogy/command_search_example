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
        <VirtualList
          items={results}
          let:item
          height='calc(100vh - 146px)'
        >
          <li
            class='{item.scrollIdx % 2 && "odd"}'
            on:click={_ => selectedQuake = item}
          >
            <div class='size'>
              {item.eq_primary}
            </div>
            <div class='country'>
              {item.country}
            </div>
            <div class='location'>
              {item.location}
            </div>
            <div class='date'>
              {item.date}
            </div>
          </li>
        </VirtualList>
      {/if}
    </ul>
  </div>
  <div class='right'>
    {#if showHelp}
      <div class='help-section'>

      </div>
    {/if}
    <Map quakes={results} bind:selectedQuake={selectedQuake}/>
    {#if selectedQuake}
      <div class='selected-quake'>
        <h4>
          Selected Quake
        </h4>
        {#each selectedAttrs as attr}
          <div on:click={_ => selectedClickAction(attr)}>
            {attr[1]}: {selectedQuake[attr[0]]}
          </div>
        {/each}
      </div>
    {/if}
  </div>
</div>

<script>
  import VirtualList from './virtualList.svelte';
  import Map from './map.svelte';

  let allIds = []
  let allQuakes = []
  let resultIds = []
  let results = []
  let selectedQuake = null
  let showHelp = false
  let query = ''

  $: results = fastQuakeFilter(resultIds, allQuakes)

  const selectedAttrs = [
    ['country', 'Country'],
    ['location', 'Location'],
    ['eq_primary', 'Size'],
    ['intensity', 'Intensity'],
    ['focal_depth', 'Focal Depth'],
    ['tsu', 'Tsunami'],
    ['latitude', 'Lat'],
    ['longitude', 'Lon'],
    ['date', 'Date'],
    ['region', 'Region'],
    ['houses_destroyed', 'Houses Destroyed'],
    ['houses_damaged', 'Houses Damaged']
  ]

  const selectedClickAction = (attr) => {
    let val = selectedQuake[attr[0]]
    if (`${val}`.includes(' ')) {
      val = `"${val}"`
    }
    query = `${attr[0]}:${val}`
    searchAction()
  }

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
</script>
