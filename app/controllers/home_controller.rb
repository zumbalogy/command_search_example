class HomeController < ApplicationController

  def splash
    render 'splash'
  end

  def search
    decoded = Base64.decode64(params[:query] || '')
    res = Earthquake.search(decoded).pluck(:_id)
    render json: res
  end
end
