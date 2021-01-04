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

<script>
  export let selectedQuake = null
  export let query = null
  export let searchAction = () => {}

  const selectedClickAction = (attr) => {
    let val = selectedQuake[attr[0]]
    if (`${val}`.includes(' ')) {
      val = `"${val}"`
    }
    query = `${attr[0]}:${val}`
    searchAction()
  }

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
</script>
