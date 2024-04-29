package com.newswhip.service;

import java.io.IOException;

public interface InputDataSource {
    CommandElement getParsedCommand() throws IOException;

}
