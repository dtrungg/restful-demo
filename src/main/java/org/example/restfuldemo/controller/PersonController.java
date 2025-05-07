package org.example.restfuldemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/person")
public class PersonController {
}
