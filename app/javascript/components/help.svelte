<div class='help-section'>
  <h4>
    Help
  </h4>
  <div class='help-subheader'>
    {helpText}
    Further details can be found
    <a href='https://github.com/zumbalogy/command_search#syntax' target='_blank'>
      here
    </a>
    .
    <br/>
    Here are some clickable examples:
  </div>
  {#each helpExamples as text}
    <div class='example' on:click={_ => clickAction(text)}>
      {text}
    </div>
  {/each}
  <div class='help-footer'>
    Click on an earthquake in the list or on the map to select it.
    <br/>
    Attributes in the selected quake are also clickable.
  </div>
</div>

<script>
  export let clickAction = () => {}

  const helpText = 'The search field allows for specification of fields (:), comparisons (< > <= >=), negation (-), a logical OR (|), quotation, and grouping via parentheses.'

  const helpExamples = [
    'country:Italy',
    '"south island"',
    'size>8.6',
    '-size:0',
    'sea|ocean',
    '100_years_ago<date<90_years_ago',
    "country:'uk' -territory",
    'morocco -spain'
  ]
</script>
