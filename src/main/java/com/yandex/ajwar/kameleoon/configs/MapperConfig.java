package com.yandex.ajwar.kameleoon.configs;

import com.yandex.ajwar.kameleoon.dtos.QuoteDto;
import com.yandex.ajwar.kameleoon.dtos.QuoteFullDto;
import com.yandex.ajwar.kameleoon.dtos.VoteQuoteDto;
import com.yandex.ajwar.kameleoon.dtos.VoteQuoteFullDto;
import com.yandex.ajwar.kameleoon.entities.sql.Quote;
import com.yandex.ajwar.kameleoon.entities.sql.VoteQuote;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper mapper = new ModelMapper();
        //full introduction of names in classes
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mapper.addMappings(quoteDtoToEntity());
        mapper.addMappings(quoteToFullDto());

        mapper.addMappings(voteQuoteDtoToEntity());
        mapper.addMappings(voteQuoteToFullDto());
        return mapper;
    }

    private PropertyMap<QuoteDto, Quote> quoteDtoToEntity() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                map(source.getUserId(), destination.getUser().getId());
            }
        };
    }

    private PropertyMap<Quote, QuoteFullDto> quoteToFullDto() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                map(source.getUser().getId(), destination.getUserId());
            }
        };
    }

    private PropertyMap<VoteQuoteDto, VoteQuote> voteQuoteDtoToEntity() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                map(source.getUserId(), destination.getUser().getId());
                map(source.getQuoteId(), destination.getQuote().getId());
            }
        };
    }

    private PropertyMap<VoteQuote, VoteQuoteFullDto> voteQuoteToFullDto() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                map(source.getUser().getId(), destination.getUserId());
            }
        };
    }
}
