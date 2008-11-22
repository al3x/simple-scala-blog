Dir.glob(File.join("../posts/**", "*.textile")).each do |filename|
  next if File.ftype(filename) == "link"

  fixed_body = ""

  File.open(filename, 'r') do |f|
    body = f.read
    fixed_body = body.gsub(/%\(=caps\)([\w|\s]+)%/, '\1')
  end

  File.open(filename, 'w') do |f|
    f.write(fixed_body)
  end

  puts "fixed #{filename}"
end
