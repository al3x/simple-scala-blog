Dir.glob(File.join("../posts/**", "*.textile")).each do |filename|
  next if File.ftype(filename) == "link"

  fixed_body = ""

  File.open(filename, 'r') do |f|
    body = f.read
    body.gsub!(/’/, "'")
    body.gsub!(/‘/, "'")
    body.gsub!(/“/, '"')
    body.gsub!(/”/, '"')

    fixed_body = body
    #puts fixed_body
  end

  File.open(filename, 'w') do |f|
    f.write(fixed_body)
  end

  puts "fixed #{filename}"
end
