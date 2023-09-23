Rails.application.routes.draw do
  get '/', to: 'home#splash'
  get '/search/', to: 'home#search'
  get '/search/:query', to: 'home#search'
  get '/search/@/:query', to: 'home#search'
end
