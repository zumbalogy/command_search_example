require('csv')
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
        tsunami: :flag_tsunami,
        flag_tsunami: Boolean,
        date: Date,
        focal_depth: Numeric,
        strength: :eq_primary,
        eq_primary: Numeric,
        intensity: Numeric,
        country: String,
        state: String,
        location: :location_name,
        location_name: String,
        lat: :latitude,
        long: :longitude,
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
        /\$\d+/ => -> (match) { "cost:#{match[1..-1]}" },
        # /=/ => ':'
      }
    }
    CommandSearch.search(Earthquake, query, options)
  end

  # def self.to_csv()
  #   attributes = Earthquake.fields.map(&:first) - ['_id']
  #   quakes = Earthquake.all.to_a
  #
  #   CSV.generate(headers: true) do |csv|
  #     csv << attributes
  #
  #     quakes.each do |quake|
  #       csv << attributes.map { |attr| quake.send(attr) }
  #     end
  #   end
  # end
  #
  # def self.export_csv()
  #   File.open(Rails.root + 'public/quake_export.csv', 'w') do |f|
  #     f.write(self.to_csv())
  #   end
  # end
  #
  # def self.export_json()
  #   File.open(Rails.root + 'public/quake_export.json', 'w') do |f|
  #     f.write(self.all.to_json())
  #   end
  # end
end
