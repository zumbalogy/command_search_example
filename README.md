Data source: https://www.ngdc.noaa.gov/nndc/struts/form?t=101650&s=1&d=1

This is a demo of Command Search: https://github.com/zumbalogy/command_search
of which the relevant code is here: https://github.com/zumbalogy/command_search_example/blob/master/app/models/earthquake.rb

map from: https://commons.wikimedia.org/wiki/File:BlankMap-Equirectangular.svg

## Setup

To run this project, you will need to have mongo installed, and then run
`bundle install` and `rake run db:seed`, then `boot dev` for the front end and `rails s` for the backend.

To build for production:

boot prod; RAILS_ENV=production rake assets:precompile; RAILS_SERVE_STATIC_FILES=true RAILS_ENV=production rails s

but note that for production, `ENV['MONGODB_URI']` has to be set (or edit mongoid.yml).

----------------------//////////////////////////////
----------------------//////////////////////////////
----------------------//////////////////////////////
----------------------//////////////////////////////

TODO:

have them be properly sorted by date.

----------------------//////////////////////////////

add time of day field.

----------------------//////////////////////////////

current readme example in command search repo of /=/ => ":" does not work, and should be tested and all

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
