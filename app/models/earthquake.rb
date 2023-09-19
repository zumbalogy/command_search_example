EARTHQUAKE_DATA = JSON.parse(File.read(__dir__ + '/../../public/quake_export.json'))

class Earthquake
  def self.search(query)
    options = {
      fields: {
        id: Numeric,
        tsunami: :tsu,
        tsu: Boolean,
        date: Date,
        focal_depth: Numeric,
        strength: :eq_primary,
        size: :eq_primary,
        eq_primary: { type: Numeric, general_seach: true },
        intensity: Numeric,
        country: { type: String, general_search: true },
        state: String,
        place: :location,
        location: { type: String, general_search: true },
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
    # Typically this would be "self" or the class name, assuming an activeRecord or mongoid class.
    CommandSearch.search(EARTHQUAKE_DATA, query, options)
  end
end
