{#if quakes.length === 0}
  <div class='map-wrapper'>
    <img src='/blank_map2.svg'>
  </div>
{:else}
  <div class='map-wrapper'>
    <img src='/blank_map2.svg'>
    <svg x='0' y='0' viewbox='0 0 360 180'>
      {#each quakes as quake}
        <circle
          cx='{180 + quake.longitude}'
          cy='{90 - quake.latitude}'
          r='2'
          fill='#1277'
        >
        </circle>
      {/each}
    </svg>
  </div>
{/if}

<script>
  export let quakes
</script>
