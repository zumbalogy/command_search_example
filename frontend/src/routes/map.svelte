{#if quakes.length === 0}
  <div class='map-wrapper'>
    <img src={blankMapSVG}>
  </div>
{:else}
  <div class='map-wrapper'>
    <img src={blankMapSVG} style='transform: {transform};'>
    <svg
      x='0'
      y='0'
      viewbox='0 0 360 180'
      style='transform: {transform};'
    >
      {#each quakes as quake}
        <circle
          cx='{180 + quake.longitude}'
          cy='{90 - quake.latitude}'
          r='{radius}'
          fill='#1277'
          on:click={_ => selectedQuake = quake}
        >
        </circle>
      {/each}
      {#if selectedQuake}
        <circle
          cx='{180 + selectedQuake.longitude}'
          cy='{90 - selectedQuake.latitude}'
          r='{4 / zoom}'
          fill='#f11'
        >
      </circle>
      {/if}
    </svg>
  </div>
{/if}

<script>
  import blankMapSVG from '$lib/images/blank_map.svg';
  import { longLatFinder } from './helpers.js'

  export let quakes
  export let selectedQuake

  $: box = longLatFinder(quakes)
  $: minLat = box[0]
  $: maxLat = box[1]
  $: minLon = box[2]
  $: maxLon = box[3]

  $: midLat = (minLat + maxLat) / 2
  $: midLon = (minLon + maxLon) / 2
  $: difLat = maxLat - minLat
  $: difLon = maxLon - minLon

  $: transX = (50 * midLon) / -180
  $: transY = (50 * midLat) / 90

  $: maxDif = Math.max(1, difLon, (difLat * 2))
  $: zoom = Math.min(20, 300 / maxDif)

  $: radius = 2 / zoom
  $: transform = (zoom < 1.4) ? '' : `scale(${zoom}) translate(${transX}%, ${transY}%)`
</script>
