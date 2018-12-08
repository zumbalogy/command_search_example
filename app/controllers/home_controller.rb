class HomeController < ApplicationController

  def splash
    render 'splash'
  end

  def search
    render json: Earthquake.search(params[:query] || '').take(50)
  end
end


# 23:37:56 boot.1  | Elapsed time: 0.779 sec
# 23:37:56 rails.1 | MONGODB | Server description for localhost:27017 changed from 'unknown' to 'standalone'.
# 23:37:56 boot.1  |
# 23:37:56 rails.1 | MONGODB | EVENT: #<Mongo::Monitoring::Event::TopologyChanged prev=Single new=Single>
# 23:38:03 boot.1  | Compiling ClojureScript...
# 23:38:03 rails.1 | MONGODB | There was a change in the members of the 'single' topology.
# 23:38:03 boot.1  | • main.js
# 23:38:03 rails.1 | MONGODB | localhost:27017 | command_search_demo_development.find | STARTED | {"find"=>"earthquakes", "filter"=>{"$or"=>[{"country"=>/m/i}, {"location_name"=>/m/i}, {"eq_primary"=>/m/i}, {"intensity"=>/m/i}]}, "lsid"=>{"id"=><BSON::Binary:0x70182998131200 type=uuid data=0xe7d02eafe0e54f1f...>}}
# 23:38:03 boot.1  | Elapsed time: 0.552 sec
# 23:38:03 rails.1 | MONGODB | localhost:27017 | command_search_demo_development.find | SUCCEEDED | 0.003s
# 23:38:03 boot.1  |
# 23:38:03 rails.1 | MONGODB | localhost:27017 | command_search_demo_development.getMore | STARTED | {"getMore"=>104800927300050, "collection"=>"earthquakes", "lsid"=>{"id"=><BSON::Binary:0x70182998131200 type=uuid data=0xe7d02eafe0e54f1f...>}}


# TODO: look into mongo/search sometimes hanging. 
