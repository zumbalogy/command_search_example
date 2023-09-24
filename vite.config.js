import { defineConfig } from 'vite'
import ViteRails from 'vite-plugin-rails'
import { svelte } from '@sveltejs/vite-plugin-svelte';

export default defineConfig({
  plugins: [
    svelte(),
    ViteRails(),
  ],
})
