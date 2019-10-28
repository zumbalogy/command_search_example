require('csv')
require('command_search')

class Earthquake
  include Mongoid::Document

  field :id, type: Integer
  field :tsu, type: Boolean
  field :date, type: Date
  field :focal_depth, type: Integer
  field :eq_primary, type: Float
  field :intensity, type: Integer
  field :country, type: String
  field :state, type: String
  field :location, type: String
  field :latitude, type: Float
  field :longitude, type: Float
  field :region, type: Integer
  field :deaths, type: Integer
  field :missing, type: Integer
  field :injuries, type: Integer
  field :damage_millions_dollars, type: Float
  field :houses_destroyed, type: Integer
  field :houses_damaged, type: Integer

  def self.search(query)
    options = {
      fields: [:country, :location, :eq_primary],
      command_fields: {
        id: Numeric,
        tsunami: :tsu,
        tsu: Boolean,
        date: Date,
        focal_depth: Numeric,
        strength: :eq_primary,
        size: :eq_primary,
        eq_primary: Numeric,
        intensity: Numeric,
        country: String,
        state: String,
        place: :location,
        location: String,
        lat: :latitude,
        long: :longitude,
        latitude: Numeric,
        longitude: Numeric,
        region: Numeric,
        deaths: Numeric,
        missing: Numeric,
        injuries: Numeric,
        damage_millions_dollars: Numeric,
        houses_destroyed: Numeric,
        houses_damaged: Numeric
      },
      aliases: {
        'asia' => '(region:30|region:40|region:50|region:60|region:140|country:MALAYSIA|country:indonesia|country:philippines -country:ecuador -country:AUSTRALIA -country:MADAGASCAR -ocean -country:COMOROS -edward)',
        'europe' => '(region:120|region:130|region:110|turkey)',
        'africa' => '(region:10|region:15|madagascar|comoros)',
        /\$\d+/ => -> (match) { "cost:#{match[1..-1]}" },
        /=/ => ':'
      }
    }
    CommandSearch.search(Earthquake, query, options)
  end
end
