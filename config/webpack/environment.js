const clientLoader = require('./loaders/svelte')
const serverLoader = require('./loaders/svelte-ssr')
const { environment } = require('@rails/webpacker')

const getSvelteEnvironments = require('svelte_ujs_ng/getSvelteEnvironments')

module.exports = getSvelteEnvironments(clientLoader, serverLoader)
