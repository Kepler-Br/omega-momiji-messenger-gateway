rootProject.name = "omega-momiji-messenger-gateway"

include("api")
include("server")

findProject(":api")?.name = "${rootProject.name}-api"
