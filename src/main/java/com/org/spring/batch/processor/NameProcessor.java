package com.org.spring.batch.processor;

import com.org.spring.batch.pojo.Name;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
@NoArgsConstructor
public class NameProcessor implements ItemProcessor<Name, Name> {


    @Override
    public Name process(final Name name) throws Exception
    {
        return name;
    }

}
