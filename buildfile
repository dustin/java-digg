# -*- ruby -*-
# Version number for this release
VERSION_NUMBER = `git describe`.strip
# Version number for the next release
NEXT_VERSION = VERSION_NUMBER
# Group identifier for your projects
GROUP = "spy"
COPYRIGHT = "2007  Dustin Sallings"

# Download stuff.
MAVEN_1_RELEASE = true
RELEASE_REPO = 'http://bleu.west.spy.net/~dustin/repo'
PROJECT_NAME = 'digg'
RELEASED_VERSIONS=%W(#{VERSION_NUMBER} 1.2 1.1.6 1.0)

# Specify Maven 2.0 remote repositories here, like this:
repositories.remote << "http://repo1.maven.org/maven2/"
repositories.remote << "http://bleu.west.spy.net/~dustin/m2repo/"

require 'buildr/java/emma'

plugins=[
  'spy:site:rake:1.2.4',
  'spy:git_tree_version:rake:1.0',
  'spy:build_info:rake:1.1.1'
]

plugins.each do |spec|
  artifact(spec).tap do |plugin|
    plugin.invoke
    load plugin.name
  end
end

desc "The Digg project"
define "digg" do

  test.options[:java_args] = "-ea"
  test.include "*Test"

  TREE_VER=tree_version
  puts "Tree version is #{TREE_VER}"
  project.version = VERSION_NUMBER
  project.group = GROUP
  manifest["Implementation-Vendor"] = COPYRIGHT
  # Regular build
  compile.with "commons-httpclient:commons-httpclient:jar:3.1-rc1",
    "commons-logging:commons-logging:jar:1.1",
    "commons-codec:commons-codec:jar:1.3",
    "junit:junit:jar:4.3.1"

  # Gen build
  gen_build_info "net.spy.digg", "git"
  compile.from "target/generated-src"
  resources.from "target/generated-rsrc"

  # I want a jar
  package(:jar).with :manifest =>
  	manifest.merge("Main-Class" => "net.spy.digg.BuildInfo\n")

end
# vim: syntax=ruby et ts=2
