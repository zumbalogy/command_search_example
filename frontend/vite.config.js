import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vite';
import { viteSingleFile } from "vite-plugin-singlefile"

import html from '@rollup/plugin-html'


export default defineConfig({
	plugins: [
		sveltekit(),
		html(),
	],
	build: {
		minify: true,
	}
});
