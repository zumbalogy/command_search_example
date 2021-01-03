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
        <VirtualList items={results} height='500px' let:item>
        <!-- {#each results as quake} -->
          <li on:click={_ => selectedQuake = item}>
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
        <!-- {/each} -->
        </VirtualList>
      {/if}
    </ul>
  </div>
  <div class='right'>
    <Map quakes={results}/>
    {#if selectedQuake}
      <div class='selected-quake'>
        <h4>
          Selected Quake
        </h4>
        {#each selectedAttrs as attr}
          <!-- TODO: make quotes only when nessesary -->
          <div
            on:click={_ => {
              query = `${attr[0]}:${selectedQuake[attr[0]]}`
              searchAction()
            }}
          >
            {attr[1]}: {selectedQuake[attr[0]]}
          </div>
        {/each}
      </div>
    {/if}
  </div>
</div>

<script>
  import VirtualList from '@sveltejs/svelte-virtual-list';
  import Map from './map.svelte';

  let allQuakes = []
  let results = []
  let selectedQuake = null
  let showHelp = false
  let query = ''

  const selectedAttrs = [
    ['country', 'Country'],
    ['location', 'Location'],
    ['eq_primary', 'Size'],
    ['intensity', 'Intensite'],
    ['focal_depth', 'Focal Depth'],
    ['tsu', 'Tsunami'],
    ['latitude', 'Lat'],
    ['longitude', 'Lon'],
    ['date', 'Date'],
    ['region', 'Region'],
    ['houses_destroyed', 'Houses Destroyed'],
    ['houses_damaged', 'Houses Damaged']
  ]

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
