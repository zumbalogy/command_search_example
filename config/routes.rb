Rails.application.routes.draw do
  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
  get '/', to: 'home#splash'
  get '/search/', to: 'home#search'
  get '/search/:query', to: 'home#search'
end
