class HomeController < ApplicationController

  def splash
    render 'splash'
  end

  def search_with_full_return
      decoded = Base64.decode64(params[:query] || '')
      res = Earthquake.search(decoded)
      render json: res
    end

  def search
    decoded = Base64.decode64(params[:query] || '')
    # res = Earthquake.order_by(_id: -1).search(decoded).pluck(:_id) # TODO: order in the database by datetime
    res = Earthquake.search(decoded).pluck(:_id)
    render json: res
  end
end
