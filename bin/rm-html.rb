Dir.glob(File.join("../posts/**", "*.html")).each do |filename|
  next if File.ftype(filename) == "link"
  File.delete(filename)
  puts "deleted #{filename}"
end
