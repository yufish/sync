The OpenShift `jbosseap` cartridge documentation can be found at:

http://openshift.github.io/documentation/oo_cartridge_guide.html#jbosseap


TODO:
- include zanata user name in the commit message
- use gitlab commit hook to trigger the job
- have a button in zanata to push translation to git repo
- potentially multiple projects (zanata.xml) in one repo
- make system properties reloadable (e.g. no need to restart the app)
- idea: use npm install --save react-redux-toastr for better message display
- techdebt: npm WARN deprecated minimatch@2.0.10: Please update to minimatch
  3.0.2 or higher to avoid a RegExp DoS issue (used by babel-core)

## Architectural diagram
```

             +-----------------+                        +------------------+
+----------+ | Sync UI App     |                        | Job execution App|
|Single    | | (artifact from  |                        | (artifact from   |
|Page      | | sync-config-war |                        |  jobs-war module)+-+
|App       | | module)         |                        |                  | +-+
|(React.js)| |                 |                        | -actual work done| | |
|          +->                 |                        |  here(git,zanata)| | |
|          | |                 |                        | -faceless REST   | | |
|          <-+   +----------+  |     quartz job run     |  only            | | |
|          | |   | quartz   +-------------------------> | -scalable        | | |
|          | |   | scheduler|  |  triggers a REST call  |                  | | |
+----------+ |   +----------+  |                        |                  | | |
             |                 |      REST post back    |                  | | |
             |                 | <--------------------+ |                  | | |
             +--+----------+---+     job run status     +-+----------------+ | |
                |          ^                              |                  | |
                |          |                              ++-----------------+ |
                v          |                               |                   |
               +----------+-+                              +-------------------+
               |Database    |
               |(Job config)|
               |(Job run    |
               | history)   |
               |...         |
               +------------+
```

### Tips

- use ```./build.sh``` to build and deploy to your local server (-H to see help)
- use ```./openshift_deploy_config_war.sh``` to deploy sync-config-war artifact
  to openshift.
- use ```./openshift_deploy_jobs_war.sh``` to deploy jobs-war artifact to
  openshift.
