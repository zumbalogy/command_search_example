import { defineConfig } from 'vite'
// import RubyPlugin from 'vite-plugin-ruby'
import ViteRails from 'vite-plugin-rails'
import { svelte } from '@sveltejs/vite-plugin-svelte';

export default defineConfig({
  plugins: [
    // sveltekit(),
    // RubyPlugin(),
    svelte(),
    ViteRails(),
  ],
})
