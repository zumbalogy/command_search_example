const clientLoader = require('./loaders/svelte')
const serverLoader = require('./loaders/svelte-ssr')

const getSvelteEnvironments = require('svelte_ujs_ng/getSvelteEnvironments')

envs = getSvelteEnvironments(clientLoader, serverLoader)

module.exports = envs
