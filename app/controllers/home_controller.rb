class HomeController < ApplicationController

  def splash
    render 'splash'
  end

  def search
    decoded = Base64.decode64(params[:query] || '')
    render json: Earthquake.search(decoded).take(100)
  end
end
