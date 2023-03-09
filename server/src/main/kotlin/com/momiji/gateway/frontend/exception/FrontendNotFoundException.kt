package com.momiji.gateway.frontend.exception

class FrontendNotFoundException(frontendName: String) :
    RuntimeException("Frontend \"$frontendName\" not found")
