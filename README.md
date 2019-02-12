### Earthquake Search

This is a Ruby on Rails project to show off usage of the command_search gem.

Live version: [earthquake-search.herokuapp.com](https://earthquake-search.herokuapp.com/).

The command_search gem: [github.com/zumbalogy/command_search](https://github.com/zumbalogy/command_search).

The relevant code is in [app/models/earthquake.rb](https://github.com/zumbalogy/command_search_example/blob/master/app/models/earthquake.rb).

Earthquake data source: [ngdc.noaa.gov/nndc/struts/form?t=101650&s=1&d=1](https://www.ngdc.noaa.gov/nndc/struts/form?t=101650&s=1&d=1)

Map source: [commons.wikimedia.org/wiki/File:BlankMap-Equirectangular.svg](https://commons.wikimedia.org/wiki/File:BlankMap-Equirectangular.svg)

### Setup

To run this project, mongo will need to be installed.
To build: `bundle install` and `rake run db:seed`.
To run: `boot dev` for the front end and `rails s` for the backend.

For production:

`boot prod; RAILS_ENV=production rake assets:precompile; RAILS_SERVE_STATIC_FILES=true RAILS_ENV=production rails s`

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

consider, in the seeds.rb, using nils when the value is nil as opposed to converting it to zero.
maybe with -1 or something. nil is more realistic for the demo though.

----------------------//////////////////////////////

make a note in command_search readme about how most aliases should probably be put in parens to play nice with others.

----------------------//////////////////////////////
----------------------//////////////////////////////
----------------------//////////////////////////////
----------------------//////////////////////////////


command_search bug:

2.5.3 :001 > pp(Earthquake.search('-(region:30|region:40)').selector) && nil
{"$or"=>[{"region"=>{"$ne"=>30}}, {"region"=>{"$ne"=>40}}]}
 => nil
2.5.3 :002 > pp(Earthquake.search('-(country:foo|country:bar)').selector) && nil
{"$or"=>[{"country"=>{"$not"=>/foo/i}}, {"country"=>{"$not"=>/bar/i}}]}

that OR should be an AND.
