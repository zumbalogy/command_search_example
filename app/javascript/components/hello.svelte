<h1>
	<title>Hello {name}! ❤</title>
</h1>

<h1>Hellosd {name}! ❤</h1>

{#if time}
  <code>{time}</code>
{/if}

<script>
  import { onMount } from 'svelte'

  export let name
  let time

  onMount(() => {
		time = new Date()

    const interval = setInterval(() => {
      time = new Date()
    }, 100)

    return () => {
      clearInterval(interval)
    }
  })
</script>
