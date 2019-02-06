Data source: https://www.ngdc.noaa.gov/nndc/struts/form?t=101650&s=1&d=1

This is a demo of Command Search: https://github.com/zumbalogy/command_search
of which the relevant code is here: https://github.com/zumbalogy/command_search_example/blob/master/app/models/earthquake.rb

map from: https://commons.wikimedia.org/wiki/File:BlankMap-Equirectangular.svg

To run this project, first `bundle install` and `rake run db:seed`, then `boot dev` for the front end and `rails s` for the backend.

To build for production:

boot prod; RAILS_ENV=production rake assets:precompile; RAILS_SERVE_STATIC_FILES=true RAILS_ENV=production rails s

TODO:

have them be properly sorted by date.

----------------------//////////////////////////////

add time of day field.

----------------------//////////////////////////////

add "how to run locally (have mongo, bundle, seed, foreman start, go to port)" and such to readme

----------------------//////////////////////////////

current readme example in command search repo of /=/ => ":" does not work, and should be tested and all

----------------------//////////////////////////////

look into tuning mongo, or configuring it, or putting indexes by fields i care about for general search (or combining them into one field to search on)

----------------------//////////////////////////////

consider, in the seeds.rb, using nils when the value is nil as opposed to converting it to zero.
maybe with -1 or something. nil is more realistic for the demo though.

---------//////////-------------------------------------------////////////------
////////////-----------------------------------------------------///////////////
---------//////////-------------------------------------------////////////------
////////////-----------------------------------------------------///////////////
---------//////////-------------------------------------------////////////------
////////////-----------------------------------------------------///////////////
---------//////////-------------------------------------------////////////------
////////////-----------------------------------------------------///////////////
---------//////////-------------------------------------------////////////------
////////////-----------------------------------------------------///////////////

TODO for command_search gem

----------------------//////////////////////////////

make things case insensitive by default and all, so clicking in selected box is nice and all

----------------------//////////////////////////////

2.5.3 :003 > Earthquake.where(eq_primary: 4).count
 => 8
2.5.3 :004 > Earthquake.where(eq_primary: "4").count
 => 8
2.5.3 :005 > Earthquake.where(eq_primary: /4/i).count
 => 0

 ----------------------//////////////////////////////

 "kuril -strength:5"

 "-strength:0"

 causes and error
