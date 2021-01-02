class HomeController < ApplicationController

  def splash
    render 'splash'
  end

  def search
    decoded = Base64.decode64(params[:query] || '').force_encoding('UTF-8')
    res = Earthquake.search(decoded).map { |x| x['_id'] }
    render json: res
  end
end
