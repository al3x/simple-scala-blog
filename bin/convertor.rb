Dir.glob(File.join("../posts/**", "*.html")).each do |filename|
  next if File.ftype(filename) == "link"

  converted = `./html2textile.php #{filename}`
  puts "converted #{filename} to textile"

  new_filename = filename.split(".html")[0] + ".textile"
  File.open(new_filename, 'w') do |f|
    f.write(converted)
  end
  puts "wrote #{new_filename}"
end
