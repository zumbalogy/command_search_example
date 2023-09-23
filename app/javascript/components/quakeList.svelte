<ul>
  {#if quakes.length === 0}
    <li class='empty-message'>
      {#if query === ''}
        Loading...
      {:else}
        No Earthquakes found.
      {/if}
    </li>
  {:else}
    <VirtualList
      items={quakes}
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

<script>
  import VirtualList from './virtualList.svelte'

  export let quakes = []
  export let query = ''
  export let selectedQuake = null
</script>
