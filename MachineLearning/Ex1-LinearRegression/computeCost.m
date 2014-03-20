function J = computeCost(X, y, theta)
%COMPUTECOST Compute cost for linear regression
%   J = COMPUTECOST(X, y, theta) computes the cost of using theta as the
%   parameter for linear regression to fit the data points in X and y

% Initialize some useful values
m = length(y); % number of training examples

% You need to return the following variables correctly 
J = 0;

% ====================== YOUR CODE HERE ======================
% Instructions: Compute the cost of a particular choice of theta
%               You should set J to the cost.

% Non vectorized way to calculate J:
soma = 0;
for j = 1:m
  soma = soma + (((theta'*X(j,:)')-y(j))^2);
end
J = (1/(2*m)) * soma;

% Vectorized way to calculate J:
% J = (1/(2*m)) * ((X * theta) - y)' * ((X * theta) - y);

% =========================================================================

end
