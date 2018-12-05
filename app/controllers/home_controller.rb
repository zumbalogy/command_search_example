class HomeController < ApplicationController

  def splash
    render inline: 'test splash'
  end

  def search
    render json: Earthquake.search(params[:query])
  end
end
