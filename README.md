Data source: https://www.ngdc.noaa.gov/nndc/struts/form?t=101650&s=1&d=1

This is a demo of Command Search: https://github.com/zumbalogy/command_search
of which the relevant code is here: https://github.com/zumbalogy/command_search_example/blob/master/app/models/earthquake.rb

map from: https://commons.wikimedia.org/wiki/File:BlankMap-Equirectangular.svg

rake run db:seed

boot prod; RAILS_ENV=production rake assets:precompile; RAILS_SERVE_STATIC_FILES=true RAILS_ENV=production rails s

TODO:

2.5.3 :003 > Earthquake.where(eq_primary: 4).count
 => 8
2.5.3 :004 > Earthquake.where(eq_primary: "4").count
 => 8
2.5.3 :005 > Earthquake.where(eq_primary: /4/i).count
 => 0

----------------------//////////////////////////////

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

make things case insensitive by default and all, so clicking in selected box is nice and all

----------------------//////////////////////////////

2.5.3 :006 > Earthquake.search("latitude:0 -longitude:0").first
Traceback (most recent call last):
        1: from (irb):6
Mongo::Error::OperationFailure ($not needs a regex or a document (2))

----------------------//////////////////////////////

add "how to run locally (have mongo, bundle, seed, foreman start, go to port)" and such to readme

----------------------//////////////////////////////

"kuril -strength:5"

"-strength:0"

causes and error

----------------------//////////////////////////////

current readme example in command search repo of /=/ => ":" does not work, and should be tested and all

----------------------//////////////////////////////

look into tuning mongo, or configuring it, or putting indexes by fields i care about for general search (or combining them into one field to search on)

----------------------//////////////////////////////

consider, in the seeds.rb, using nils when the value is nil as opposed to converting it to zero.
maybe with -1 or something. nil is more realistic for the demo though.
