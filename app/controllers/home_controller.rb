class HomeController < ApplicationController

  def splash
    render 'splash'
  end

  def search
    render json: Earthquake.search(params[:query])
  end
end
