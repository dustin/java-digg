# Generated by Buildr 1.2.8, change to your liking
# Version number for this release
VERSION_NUMBER = "1.3.3"
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

require 'buildr/cobertura'

# Specify Maven 2.0 remote repositories here, like this:
repositories.remote << "http://www.ibiblio.org/maven2/"
repositories.remote << "http://bleu.west.spy.net/~dustin/m2repo/"

plugins=[
  'spy:site:rake:1.2.3',
  'spy:git_tree_version:rake:1.0',
  'spy:build_info:rake:1.1'
]

plugins.each do |spec|
  artifact(spec).tap do |plugin|
    plugin.invoke
    load plugin.name
  end 
end

desc "The Digg project"
define "digg" do

  options.java_args = "-ea"

  TREE_VER=tree_version
  puts "Tree version is #{TREE_VER}"
  project.version = VERSION_NUMBER
  project.group = GROUP
  manifest["Implementation-Vendor"] = COPYRIGHT
  # Regular build
  compile.with "commons-httpclient:commons-httpclient:jar:3.1-rc1",
    "commons-logging:commons-logging:jar:1.1",
    "commons-codec:commons-codec:jar:1.3"

  # Gen build
  gen_build_info "net.spy.digg"
  compile.from "target/generated-src"
  resources.from "target/generated-rsrc"

  # I want a jar
  package(:jar).with :manifest =>
  	manifest.merge("Main-Class" => "net.spy.digg.BuildInfo\n")

  desc "Ship the build"
  define "ship" do
    system "rsync", "-vaSe", "ssh", "target/digg-#{VERSION_NUMBER}.jar",
      "basket.west.spy.net:/data/web/jboss/deploy/diggwatch.war"
  end

end
# vim: syntax=ruby et ts=2
