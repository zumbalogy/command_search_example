import html from 'bun-plugin-html';

await Bun.build({
  entrypoints: [
    './.svelte-kit/output/client/index.html',
  ],
  outdir: './dist',
  plugins: [
    html({
      inline: true, 
    })
  ],
});
