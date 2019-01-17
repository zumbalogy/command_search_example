# This file is used by Rack-based servers to start the application.

require_relative 'config/environment'

run Rails.application

require 'iodine'
# static file service
Iodine.listen2http public: (Rails.root + 'public').to_s
# for static file service, we only need a single thread per worker.
Iodine.threads = 1
Iodine.start
