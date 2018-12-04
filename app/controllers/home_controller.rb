class HomeController < ApplicationController

  def splash
    render inline: 'test splash'
  end
end
