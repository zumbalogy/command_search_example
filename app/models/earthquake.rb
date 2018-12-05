require('command_search')

class Earthquake
  include Mongoid::Document

  field :id, type: Integer
  field :flag_tsunami, type: Boolean
  field :date, type: Date
  field :focal_depth, type: Integer
  field :eq_primary, type: Float
  field :intensity, type: Integer
  field :country, type: String
  field :state, type: String
  field :location_name, type: String
  field :latitude, type: Float
  field :longitude, type: Float
  field :region_code, type: Integer
  field :deaths, type: Integer
  field :missing, type: Integer
  field :injuries, type: Integer
  field :damage_millions_dollars, type: Float
  field :houses_destroyed, type: Integer
  field :houses_damaged, type: Integer

  def self.search(query)
    options = {
      fields: [:country, :location_name, :eq_primary, :intensity],
      command_fields: {
        id: Numeric,
        flag_tsunami: Boolean,
        date: Date,
        focal_depth: Numeric,
        eq_primary: Numeric,
        intensity: Numeric,
        country: String,
        state: String,
        location_name: String,
        latitude: Numeric,
        longitude: Numeric,
        region_code: Numeric,
        deaths: Numeric,
        missing: Numeric,
        injuries: Numeric,
        damage_millions_dollars: Numeric,
        houses_destroyed: Numeric,
        houses_damaged: Numeric
      },
      aliases: {
        'favorite' => 'starred:true',
        /=/ => ':',
        /\$\d+/ => -> (match) { "cost:#{match[1..-1]}" }
      }
    }
    CommandSearch.search(Earthquake, query, options)
  end
end
