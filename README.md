Data source: https://www.ngdc.noaa.gov/nndc/struts/form?t=101650&s=1&d=1

This is a demo of Command Search: https://github.com/zumbalogy/command_search
of which the relevant code is here: https://github.com/zumbalogy/command_search_example/blob/master/app/models/earthquake.rb

map from: https://commons.wikimedia.org/wiki/File:BlankMap-Equirectangular.svg


rake run db:seed; gzip the public one. (db:seed needs to write the file. (to assets as well i guess))

boot prod; RAILS_ENV=production rake assets:precompile; RAILS_SERVE_STATIC_FILES=true RAILS_ENV=production rails s


TODO:

Started GET "/search/3w==" for 127.0.0.1 at 2018-12-31 15:02:22 -0600
Processing by HomeController#search as */*
  Parameters: {"query"=>"3w=="}
MONGODB | localhost:27017 | command_search_demo_development.find | STARTED | {"find"=>"earthquakes", "filter"=>{"$or"=>[{"country"=>/\xDF/i}, {"location_name"=>/\xDF/i}, {"eq_primary"=>/\xDF/i}, {"intensity"=>/\xDF/i}]}, "sort"=>{"_id"=>-1}, "lsid"=>{"id"=><BSON::Binary:0x70197866788300 type=uuid data=0x62c39d20480f4408...>}}
MONGODB | localhost:27017 | command_search_demo_development.find | FAILED | String � is not a valid UTF-8 CString. | 0.00021799999999999999s
Completed 500 Internal Server Error in 2ms

----------------------//////////////////////////////

ArgumentError (String � is not a valid UTF-8 CString.):

app/controllers/home_controller.rb:10:in `take'
app/controllers/home_controller.rb:10:in `search'
Started GET "/search/3w==" for 127.0.0.1 at 2018-12-31 15:02:24 -0600
Processing by HomeController#search as */*
  Parameters: {"query"=>"3w=="}
MONGODB | localhost:27017 | command_search_demo_development.find | STARTED | {"find"=>"earthquakes", "filter"=>{"$or"=>[{"country"=>/\xDF/i}, {"location_name"=>/\xDF/i}, {"eq_primary"=>/\xDF/i}, {"intensity"=>/\xDF/i}]}, "sort"=>{"_id"=>-1}, "lsid"=>{"id"=><BSON::Binary:0x70197871247660 type=uuid data=0xdd7254fd932c48f6...>}}
MONGODB | localhost:27017 | command_search_demo_development.find | FAILED | String � is not a valid UTF-8 CString. | 0.00027s
Completed 500 Internal Server Error in 2ms

------------------------

have them be properly sorted by date.

----------------------//////////////////////////////

add time of day field.

----------------------//////////////////////////////

a timeline above the map could be nice.

----------------------//////////////////////////////

make things case insensitive by default and all, so clicking in selected box is nice and all

----------------------//////////////////////////////

2.5.3 :006 > Earthquake.search("latitude:0 -longitude:0").first
Traceback (most recent call last):
        1: from (irb):6
Mongo::Error::OperationFailure ($not needs a regex or a document (2))

----------------------//////////////////////////////

look into the pending
```
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
```

----------------------//////////////////////////////

proper paging

----------------------//////////////////////////////

add "how to run locally (have mongo, bundle, seed, foreman start, go to port)" and such to readme

----------------------//////////////////////////////

some data like "Location: Grfece: Off West Coast" and should maybe just be cleaned up.

----------------------//////////////////////////////

"kuril -strength:5"

"-strength:0"

causes and error

----------------------//////////////////////////////

make the zoom in zoom a bit more by default.

----------------------//////////////////////////////

make urls searchable

----------------------//////////////////////////////


current readme example in command search repo of /=/ => ":" does not work, and should be tested and all


//////////////////////////////////////////////

TODO: gzip the quake_export.csv (put on asset pipeline or something)


///////


it should say "loading" not "no quakes found" when loading in the beggining. 
