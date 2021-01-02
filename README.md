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

Note to self: Cljs has to be precompiled before pushing to Heroku.
