# **Final Report**

###### Stephan Gunawardana

srg97@zips.uakron.edu

### Overview

To understand the differences between CircleCi and Jenkins we must first understand the main idea or a small overview of what each program does or has access too. One of the key features offered by CircleCi would include Continuous Integration. To put this concept into simple terms it essentially means a team pushing small changes to a specific Git repository and the changes are being implemented continuously rather than periodically. One of the key things CircleCi prides itself on is its accessibility with Github repositories. This is one of the many features I thought aided in adding new features to my pipeline. For example, after verifying your GitHub account with CircleCi’s website, it will automatically create a separate branch for all of the pushes. On top of this already helpful step, it will automatically run any pushes you make to the CircleCi’s branch to verify each step of the pipeline works. 

The other type of software I was tasked with using was Jenkins. Jenkins is similarly used for building and testing your product continuously or in other words continues integration. Majority of developers use it to accelerate the software development process through mainly automation. Another important fact to note about Jenkins is that it was built with Java, therefore it is portable to all the  key platforms. Lastly, similar to CircleCI Jenkins also does support a web interface, which allows the developers to have easy access to the required tools for the development process

### Differences

One of the first types of differences I noticed was the ease of use of CircleCi when compared to Jenkins. If we were not previously experienced with Docker the process of running Jenkins would have been much more difficult. I will expand on this topic later within features section. With CircleCi it was relatively simple to set up the process through the user interface provided by the CircleCi website. Another thing to note about CircleCi is as noted above, it will connect directly to your GitHub repository. In various cases this is a good thing, however within a project which requires frequent changes this can somewhat become tedious and hard to manage. This was due to the fact that to test each minor change within my project I had to continuously update my CircleCi file which led to lots of commits with confusing commit messages being updated to the coordinating CircleCi branch. Also, it seemed as if there were no Plugins available for CircleCi. The main website of CircleCi highlighted that all of the core functionality was built into the program, and if you needed to develop more functionality then you would need to use the shell scripts. Also as mentioned in one of your lessons CircleCi is considered SaaS(software as a service), which in this case means that the underlying structure for CircleCi is not visible for the developer. For example, the environment within this specific project we were developing on was not visible to us. Also, the docs for CircleCi mentions that the build environment does change without warning, while this did not seem to affect the outcome of this specific project, it might become troublesome in larger projects which require more dependencies. Thus leading to finding bugs much harder. 

Next we will discuss the key difference found within Jenkins. The first notable thing as mentioned above would be the build environment for Jenkins. For this project, it was somewhat difficult to set up the correct environment. This was due to the fact that we were dealing with two images from Docker, one which ran the Jenkins container on the local machine and also a customizable image created by the developer which our shell commands actually ran on. After getting past this, you would need to also need to give Jenkins the correct permissions, which was corrected by mounting the docker image to the Jenkins container. I also had to change all the commands to ``sh`` commands due to the fact that this would allow it to run inside of the container. While this might seem simple at first, the actual practice of it was somewhat difficult due to needing to change local permissions as well for the process to succeed. However, after getting past the initial problems of setting docker with Jenkins you begin to see the various benefits of using Jenkins. The first notable difference would be the various amounts of plugins which are available for Jenkins. While I was not able to use any plugins,excluding the ones mentioned in class, the "manage plugins" option within Jenkins showed various plugins which were available for our projects. Therefore, this allows for more expansive features compared to CircleCi's only pre-installed plugins. The last difference noticed was the fact that within Jenkins the environment will only change with user permission, compared to CircleCi's changing without permission. 

Overall,  I think if you wanted to execute a simple pipeline which required minor changes to environment then CircleCi is the better option, however if you wanted to add more plugins or more customization to the build process, but be more knowledgeable with Docker then Jenkins is the way. 

## Features

### archiveArtifacts

Within our project I ran the command ``cpack -G DEB`` which would allow for specifically Debian packages to be created within the "Build" directory. However, with the command ``archiveArtifacts`` the developer is able to access these packages directly through the Jenkins UI. Another thing to note is the ``post`` section within the pipeline is guaranteed to run at the end of the Pipeline execution and is usually used for thing such as cleaning up or saving documents. This whole snippet of code is shown below

``archiveArtifacts artifacts: 'build/*.deb', onlyIfSuccessful: true``

It is relatively simple to understand you just must specify exactly where the Debian files are found after ``artifacts:``. Also, the ``onlyIfSuccessfule:true`` will only run if the build is successful. While this might seem like a simple features, it took a long amount of time to figure out due to Jenkins environment settings. The biggest problem was ``archiveArtifacts`` was running in the Jenkins container and not within the docker container, therefore it was not finding my ``Build`` directory with a capital "B". However, I was able overcome this by first using the post section, which allowed all of the shell commands to finish and access a build file named ``build`` within the correct container. I was able to validate the features worked due to the fact that it outputted the deb files within the Jenkins UI. This solution might have been only viable on my personal Jenkins image therefore, I saved that as a public image titled ``srg97/myjenkins``.

### Caching in CircleCi + Variable Usage

The first minor thing I added to CircleCi was using the dynamic public variables available through CircleCi, they were very simple to add. The two I added includes ``$CIRCLE_USERNAME`` and ``$CIRCLE_BRANCH``. They basically echoes out which branch the developer is currently running on and also the username of the user who is running the process within CircleCi. This might seem like a trivial feature, however it can be combined with logic to do such things such as verify usage or even security. The last feature I added to this included caching the git repository. Prior to adding this feature it led to consistently checkout out a branch each time it was run on CircleCi. 

``````YML
      - restore_cache: 
          key: source-v1-{{ .Branch }}-{{ .Revision }}
``````

This bit of code above basically will look through the various keys to see if there is a match from previous runs. If there is then it will automatically cache the previous git repo if there were no revisions. It looks for a cache hit first from current revision then from any branch. Thus speeding up the whole process.  If there were then it would lead to a normal checkout.

``````yaml
      - save_cache:
          key: source-v1-{{ .Branch }}-{{ .Revision }}
          paths:
            - ".git"
``````

Lastly, this bit of code will save the cache for the current run, therefore it the next one will be cached if there were changes made on the current branch. The ``paths`` basically specifies what type of caching is taking place. 

