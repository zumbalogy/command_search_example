class HomeController < ApplicationController

  def splash
    render 'splash'
  end

  def search
    decoded = Base64.decode64(params[:query] || '')
    res = Earthquake.order_by(_id: -1).search(decoded).take(100)
    render json: res
  end
end
