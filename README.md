Data source: https://www.ngdc.noaa.gov/nndc/struts/form?t=101650&s=1&d=1

This is a demo of Command Search: https://github.com/zumbalogy/command_search
of which the relevant code is here: https://github.com/zumbalogy/command_search_example/blob/master/app/models/earthquake.rb

map from: https://commons.wikimedia.org/wiki/File:BlankMap-Equirectangular.svg

TODO:

have them be properly sorted by date.

"hawaii" zooms the map funny

add time of day field.

make "cleaned" in the disclaimer a link to the code

clicking the map could search results within a few degrees of where you clicked

a timeline above the map could be nice.

selected one could be a diff color on map/timeline

make question mark pop up helper box in the input to show off syntax and all.

build selected-result card pretty and make things clickable so that clicking country:spain puts "country:spain" in the search box

make the map take up more room

make the map zoom a little bit when it can

2.5.3 :006 > Earthquake.search("latitude:0 -longitude:0").first
Traceback (most recent call last):
        1: from (irb):6
Mongo::Error::OperationFailure ($not needs a regex or a document (2))

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

proper paging

give lat and long to the 49 with 0 and 0 as their lat and long
:008 > Earthquake.search("latitude:0 longitude>0").count
 => 5


country:"" causes (Request URL: http://localhost:5000/search/Y291bnRyeToiIg==) command search to error



 ----


 how to run locally (have mongo, bundle, seed, foreman start, go to port)
