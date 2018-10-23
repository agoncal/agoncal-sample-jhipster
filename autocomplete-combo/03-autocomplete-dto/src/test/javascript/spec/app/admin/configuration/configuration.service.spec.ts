import { TestBed } from '@angular/core/testing';

import { AudConfigurationService } from 'app/admin/configuration/configuration.service';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpResponse } from '@angular/common/http';

describe('Service Tests', () => {
    describe('Logs Service', () => {
        let service: AudConfigurationService;
        let httpMock;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });

            service = TestBed.get(AudConfigurationService);
            httpMock = TestBed.get(HttpTestingController);
        });

        afterEach(() => {
            httpMock.verify();
        });

        describe('Service methods', () => {
            it('should call correct URL', () => {
                service.get().subscribe(() => {});

                const req = httpMock.expectOne({ method: 'GET' });
                const resourceUrl = SERVER_API_URL + 'management/configprops';
                expect(req.request.url).toEqual(resourceUrl);
            });

            it('should get the config', () => {
                const angularConfig = {
                    contexts: {
                        angular: {
                            beans: ['test2']
                        }
                    }
                };
                service.get().subscribe(received => {
                    expect(received.body[0]).toEqual(angularConfig);
                });

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(angularConfig);
            });

            it('should get the env', () => {
                const propertySources = new HttpResponse({
                    body: [{ name: 'test1', properties: 'test1' }, { name: 'test2', properties: 'test2' }]
                });
                service.get().subscribe(received => {
                    expect(received.body[0]).toEqual(propertySources);
                });

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(propertySources);
            });
        });
    });
});
