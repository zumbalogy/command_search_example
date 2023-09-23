import adapter from '@sveltejs/adapter-static';

export default {
	kit: {
		// outDir: './dist',
		adapter: adapter({
			pages: 'build',
			assets: 'build',
			fallback: undefined,
			precompress: false,
			strict: false,
		})
	}
};
