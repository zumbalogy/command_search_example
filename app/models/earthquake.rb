class Earthquake
  include Mongoid::Document

  field :i_d, type: Integer
  field :flag_tsunami, type: Boolean
  field :year, type: Integer
  field :month, type: Integer # todo: combine into a Time
  field :day, type: Integer # todo: combine into a Time
  field :hour, type: Integer # todo: combine into a Time
  field :minute, type: Integer # todo: combine into a Time
  field :second, type: Integer # todo: combine into a Time
  field :focal_depth, type: Float
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
  field :total_deaths, type: Integer
  field :total_missing, type: Integer
  field :total_injuries, type: Integer
  field :total_damage_millions_dollars, type: Float
  field :total_houses_destroyed, type: Integer
  field :total_houses_damaged, type: Integer

end
